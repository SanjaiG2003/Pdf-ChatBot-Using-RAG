
package com.sanjai.local_rag;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DocumentController {

    private final VectorStore vectorStore;

    public DocumentController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping(
            value = "/api/documents/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public UploadResult upload(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        // 1. Extract one Document per PDF page
        List<Document> pages =
                new PagePdfDocumentReader(file.getResource()).get();

        // 2. Add filename and page number to every page
        String filename = file.getOriginalFilename();

        for (int i = 0; i < pages.size(); i++) {
            Document page = pages.get(i);

            page.getMetadata().put("file_name", filename);
            page.getMetadata().put("page_number", i + 1);
        }

        // 3. Split pages into smaller chunks
        List<Document> chunks =
                TokenTextSplitter.builder()
                        .withChunkSize(300)
                        .build()
                        .apply(pages);

        // 4. Generate embeddings and save chunks in pgvector
        vectorStore.add(chunks);

        return new UploadResult(
                filename,
                pages.size(),
                chunks.size()
        );
    }

    public record UploadResult(
            String file,
            int pages,
            int chunks
    ) {}
}
