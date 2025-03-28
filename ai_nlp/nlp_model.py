import spacy
import re
from transformers import pipeline

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


