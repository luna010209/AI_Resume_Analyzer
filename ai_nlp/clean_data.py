import re

def clean_resume(text):
    text = re.sub(r'\n+', ' ', text)  # Remove new lines
    text = re.sub(r'\s+', ' ', text)  # Remove multiple spaces
    text = re.sub(r'[^a-zA-Z0-9.,\s]', '', text)  # Remove special characters
    # Remove encoding artifacts (e.g., `?|?`, `�`)
    text = re.sub(r"[\?|\|�]", "", text)

    # Normalize dates (e.g., `2 0 2 4 . 0 6` → `2024.06`)
    text = re.sub(r"(\d) (\d{3}) (\.\d{2})", r"\1\2\3", text)

    # Fix split words (e.g., `deve loper` → `developer`)
    text = re.sub(r"(\b[a-zA-Z]+)\s+([a-zA-Z]+\b)", r"\1\2", text)

    # # Preserve URLs by skipping modification inside links
    # text = re.sub(r"https?://\S+", lambda x: x.group(0), text)
    return text.strip()
