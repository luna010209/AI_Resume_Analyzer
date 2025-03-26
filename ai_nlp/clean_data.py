import re

def clean_resume(text):
    text = re.sub(r'\n+', ' ', text)  # Remove new lines
    text = re.sub(r'\s+', ' ', text)  # Remove multiple spaces
    text = re.sub(r'[^a-zA-Z0-9.,\s]', '', text)  # Remove special characters
    return text.strip()
