import pandas as pd
from flask import Flask, request, jsonify
from langchain_openai import ChatOpenAI
from langchain_community.document_loaders import TextLoader
from langchain_community.vectorstores import FAISS
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_core.prompts import PromptTemplate
from langchain_core.runnables import RunnablePassthrough
from langchain_core.output_parsers import StrOutputParser
from langchain_community.embeddings import OpenAIEmbeddings
from dotenv import load_dotenv
import os

load_dotenv()
openai_api_key = os.getenv('OPENAI_API_KEY')

loader = TextLoader("carinfo.txt", encoding="utf-8")
docs = loader.load()

text_splitter = RecursiveCharacterTextSplitter(chunk_size=700, chunk_overlap=50)
split_documents = text_splitter.split_documents(docs)

embeddings = OpenAIEmbeddings(openai_api_key=openai_api_key)
vectorstore = FAISS.from_documents(documents=split_documents, embedding=embeddings)
retriever = vectorstore.as_retriever()

crawling_df = pd.read_csv("crawling_data.CSV", encoding="cp949")

from langchain.agents.agent_types import AgentType
from langchain_experimental.agents.agent_toolkits import create_pandas_dataframe_agent

agent = create_pandas_dataframe_agent(
    ChatOpenAI(temperature=0, model="gpt-3.5-turbo", openai_api_key=openai_api_key),
    crawling_df,
    verbose=True,
    agent_type=AgentType.OPENAI_FUNCTIONS,
    allow_dangerous_code=True
)

pdf_prompt = PromptTemplate.from_template(
    """너는 중고차나 자동차 관련 정보를 제공해줘야 해.
    관련 질문에 답변을 하기 위해 아래의 context를 참고하렴.
    답변을 할 때는 그렇게 판단하는 이유도 자세히 포함시켜주면 좋겠어.
    잘 모르는 질문에 대해서는 굳이 답변하지 않아도 돼.
    Answer in Korean.

    #Context:
    {context}

    #Question:
    {question}

    #Answer:"""
)

csv_prompt = PromptTemplate.from_template(
    """너는 조건에 맞는 중고차 매물을 추천해줘야 해.
    중고차 매물을 추천하기 위해 아래의 context를 참고하렴.
    조건에 맞는 매물이 많다면, 3개를 랜덤으로 선택해 실제 링크를 포함한 매물 정보를 제공해줘.
    조건에 맞는 매물이 없을 경우, 조건에 맞는 매물이 없으니 다른 조건을 제시해달라고 요청해줘.
    
    #Context:
    {context}

    #Question:
    {question}

    #Answer:"""
)

llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0, openai_api_key=openai_api_key)

def get_response(question):
    if "매물" in question and "추천" in question:
        chain = (
            {"context": agent.invoke, "question": RunnablePassthrough()}
            | csv_prompt
            | llm
            | StrOutputParser()
        )
    else:
        chain = (
            {"context": retriever, "question": RunnablePassthrough()}
            | pdf_prompt
            | llm
            | StrOutputParser()
        )

    response = chain.invoke(question)
    return response

app = Flask(__name__)

@app.route('/chatbot', methods=['POST'])
def chatbot():
    data = request.get_json()
    question = data.get("question")

    if not question:
        return jsonify({"error": "No question provided"}), 400

    response = get_response(question)
    return jsonify({"response": response})

if __name__ == "__main__":
    app.run(port=5000)