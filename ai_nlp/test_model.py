import spacy
import re
import nltk
from transformers import pipeline

nltk.download('stopwords')

# Load spaCy model
nlp= spacy.load("en_core_web_sm")

ner_pipeline= pipeline("token-classification", model="dslim/bert-base-NER")

def extract_basic_info(text):
  doc = nlp(text)

  # Extract Name
  name = text((ent.text for ent in doc.ents if ent.label_ == "PERSON"))

  # Extract Email
  email_match = re.search(r"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}", text)
  email = email_match.group(0) if email_match else "Not Found"

  # Extract Phone Number
  phone_match = re.search(r"\(?\d{3}\)?[-.\s]?\d{4}[-.\s]?\d{4}", text)
  phone = phone_match.group(0) if phone_match else "Not Found"

  return {"Name": name, "Email": email, "Phone": phone}

def extract_skills(text):
  skills_list = ["Python", "Java", "SQL", "React", "AWS", "Docker", "Machine Learning", "Deep Learning", "Data Science"]
  found_skills = [skill for skill in skills_list if skill.lower() in text.lower()]
    
  return {"Skills": found_skills}


import streamlit as st
import pandas as pd
import base64, random
import time, datetime
from pyresparser import ResumeParser
from pdfminer3.layout import LAParams, LTTextBox
from pdfminer3.pdfpage import PDFPage
from pdfminer3.pdfinterp import PDFResourceManager
from pdfminer3.pdfinterp import PDFPageInterpreter
from pdfminer3.converter import TextConverter
import io, random
from streamlit_tags import st_tags
from PIL import Image
import pymysql
import pafy
import plotly.express as px

def fetch_yt_video(link):
  video = pafy.new(link)
  return video.title

def get_table_download_link(df, filename, text):
  """Generates a link allowing the data in a given panda dataframe to be downloaded
  in: dataframe
  out: href string
  """
  csv = df.to_csv(index= False)
  b64 = base64.b64encode(csv.encode()).decode()
  href = f'<a href="data:file/csv;base64,{b64}" download="{filename}">{text}</a>'
  return href

def pdf_reader(file):
  resource_manager = PDFResourceManager()
  fake_file_handle = io.StringIO()
  converter = TextConverter(resource_manager, fake_file_handle, laparams=LAParams())
  page_interpreter = PDFPageInterpreter(resource_manager, converter)
  with open(file, 'rb') as f:
    for page in PDFPage.get_pages(f, caching=True, check_extractable=True):
      page_interpreter.process_page(page)
      print(page)
    text = fake_file_handle.getvalue()

  converter.close()
  fake_file_handle.close()
  return text

def show_pdf(file_path):
  with open(file_path, "rb") as f:
    base64_pdf = base64.b64encode(f.read()).decode('utf-8')
  pdf = F'<iframe src="data:application/pdf;base64,{base64_pdf}" width="700" height="1000" type="application/pdf"></iframe>'
  st.markdown(pdf, unsafe_allow_html=True)

# def basic_data(name, email, res_score, timestamp, no_of_pages, reco_field, courses):
  