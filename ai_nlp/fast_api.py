from fastapi import FastAPI, UploadFile, File
import main_model
from clean_data import clean_resume

app = FastAPI()
@app.post("/analyze/")
async def analyze_resume_api(file: UploadFile = File(...)):
  # file_path = f"{file.filename}"

  # with open(file_path, "wb") as buffer:
  #   buffer.write(await file.read())

  with open(file.filename, "r") as f:
    text = clean_resume(f.read())

    
  result = main_model.analyze_resume(text)

  return {"filename": file.filename, "analysis": result}
