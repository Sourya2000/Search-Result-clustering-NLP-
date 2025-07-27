package com.Indexer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.Constants.Constants;
import com.analyser.LuceneAnalyser;

public class IndexDocumentCollection {

	public static void indexDocuments() throws IOException {
		System.out.println("Inside indexDocuments() ");
		Path indexPath = Paths.get(Constants.INDEXED_DOCUMENTS_PATH);
		Directory directory = FSDirectory.open(indexPath);

 		// Clear existing indexed documents 
		try {
			clearDirectory(indexPath);
			System.out.println("Previously Indexed files in Index Directory cleared successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		startIndexing(directory);
	}

	private static void startIndexing(Directory directory) throws IOException {
		CustomAnalyzer customAnalyzer = LuceneAnalyser.getAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(customAnalyzer);
		IndexWriter iWriter = new IndexWriter(directory, config);
		File documentCollectionDir = new File(Constants.DOCUMENTS_COLLECTION_PATH);
		File[] documentFiles = documentCollectionDir.listFiles();

		for (File documentFile : documentFiles) {

			try {
				Document doc = new Document();

				String fileExtract = FileUtils.readFileToString(documentFile, StandardCharsets.UTF_8);
				Field docExtractField = new Field(Constants.DOC_FIELD_EXTRACT_STRING, fileExtract,
						TextField.TYPE_STORED);
				doc.add(docExtractField);

				String fileNameString = documentFile.getName().toString().replace(Constants.DOC_FILE_EXTENTSION_STRING,
						Constants.DOC_FILE_EXTN_REPLACEMENT_STRING);
				Field docNameField = new Field(Constants.DOC_FIELD_NAME_STRING, fileNameString, TextField.TYPE_STORED);
				doc.add(docNameField);

				iWriter.addDocument(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		iWriter.close();
	}

	private static void clearDirectory(Path directory) throws IOException {
		// Delete all files and sub-directories
		Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
				new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Files.delete(file);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						// Handle the failure to visit a file (e.g., permission issue)
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						// Skip deleting the root directory itself
						if (dir.equals(directory)) {
							return FileVisitResult.CONTINUE;
						}

						// Delete sub-directories and their contents
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					}
				});
	}
}
