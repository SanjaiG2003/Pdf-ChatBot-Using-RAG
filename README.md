
# PDF ChatBot Using RAG

A Retrieval-Augmented Generation (RAG) application that allows users to upload PDF documents and ask questions about their contents.

The application uses Spring Boot, Spring AI, Ollama, Gemma 4 and PostgreSQL with PGVector to retrieve relevant document content and generate context-based answers.

## Features

- Upload PDF documents.
- Extract text from PDF files.
- Split document content into smaller chunks.
- Generate embeddings using Ollama.
- Store document embeddings in PostgreSQL using PGVector.
- Retrieve relevant document chunks using similarity search.
- Generate answers using Gemma 4.
- Display answers with document filename and page citations.
- Simple HTML interface for uploading PDFs and asking questions.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Backend framework |
| Spring AI | AI integration |
| Ollama | Local AI model runtime |
| Gemma 4 (gemma4:e2b) | Chat model |
| Nomic Embed Text | Embedding model |
| PostgreSQL | Database |
| PGVector | Vector storage and similarity search |
| HTML, CSS, JavaScript | Frontend |
| Gradle | Build tool |

## Architecture

```text
             User
              |
              v
         HTML Interface
              |
              v
        Spring Boot API
              |
       +------+------+
       |             |
       v             v
   PDF Upload    User Question
       |             |
       v             v
   PDF Reader    Embedding
       |             |
       v             v
  Text Splitting  PGVector Search
       |             |
       v             v
  Embeddings     Relevant Chunks
       |             |
       v             v
    PGVector     Context + Question
                     |
                     v
                  Gemma 4
                     |
                     v
                  Answer
```

## Prerequisites

Install the following before running the application:

- Java 21
- Git
- Docker Desktop
- Ollama
- PostgreSQL with PGVector (provided through Docker Compose)

## AI Models

Pull the required models using Ollama:

```bash
ollama pull gemma4:e2b
ollama pull nomic-embed-text
```

Make sure Ollama is running before starting the application.

## Database Setup

Start PostgreSQL with PGVector using Docker Compose:

```bash
docker compose up -d
```

The application uses PostgreSQL for storing document embeddings.

Configure your database connection in `application.yml` or through environment variables.

## Run the Application

Clone the repository:

```bash
git clone https://github.com/SanjaiG2003/Pdf-ChatBot-Using-RAG.git
```

Navigate into the project:

```bash
cd Pdf-ChatBot-Using-RAG
```

Run the application on Windows:

```bash
gradlew.bat bootRun
```

The application runs at:

```text
http://localhost:8080
```

Open the application in your browser.

## How to Use

1. Start Docker Desktop.
2. Start Ollama and ensure the required models are available.
3. Run the Spring Boot application.
4. Open `http://localhost:8080`.
5. Select and upload a PDF document.
6. Wait for document processing to finish.
7. Enter a question about the uploaded document.
8. View the generated answer with source references.

## API Endpoints

### Upload PDF

```http
POST /api/documents/upload
```

Uploads a PDF, extracts its content, splits it into chunks and stores embeddings in PGVector.

### Ask a Question

```http
GET /api/ask?question=YourQuestion
```

Retrieves relevant document chunks and generates an answer using the chat model.

## Configuration

The application uses the following AI models:

```yaml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: gemma4:e2b
          temperature: 0.2
      embedding:
        options:
          model: nomic-embed-text
```

Ensure database credentials and other sensitive configuration values are not committed to GitHub.

## Project Structure

```text
Pdf-ChatBot-Using-RAG/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/kloudocean/localrag/
│       │       ├── LocalRagApplication.java
│       │       ├── ChatController.java
│       │       └── DocumentController.java
│       │
│       └── resources/
│           ├── static/
│           │   └── index.html
│           └── application.yml
│
├── compose.yaml
├── build.gradle
├── settings.gradle
├── .gitignore
└── README.md
```

## Important Notes

- Ollama models are downloaded locally and are not included in this repository.
- You must download the required models separately.
- PDF documents are processed and stored in the vector database.
- Do not commit passwords, API keys, personal documents or model weights.
- This application is designed to answer questions using retrieved document context.

## Future Improvements

- Support multiple PDF documents.
- Improve retrieval accuracy.
- Add document deletion functionality.
- Improve source citation accuracy.
- Add conversation history.
- Support additional document formats.

## Author

**Sanjai G**

GitHub: https://github.com/SanjaiG2003
