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

import com.Constants.ProjectConstants;
import com.analyser.MyCustomAnalyser;


public class IndexDocumentCollection {
	
	public static void indexDocuments() throws IOException {
		Path indexPath = Paths.get(ProjectConstants.INDEXED_DOCUMENTS_PATH);
	    Directory indexDir = FSDirectory.open(indexPath);
	    
//	    Clear Index Directory before indexing documents 
	    try {
            clearDirectory(indexPath);
            System.out.println("Previously Indexed files in Index Directory cleared successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	    
//	    Start indexing
	    indexer(indexDir);
     
	}
	
	private static void indexer(Directory indexDir) throws IOException {
	    CustomAnalyzer customAnalyzer = MyCustomAnalyser.getAnalyzer();
	    IndexWriterConfig config = new IndexWriterConfig(customAnalyzer);
	    IndexWriter iwriter = new IndexWriter(indexDir, config);
	    File documentCollectionDir = new File(ProjectConstants.DOCUMENTS_COLLECTION_PATH);
	    File[] documentFiles = documentCollectionDir.listFiles();
	    
//	    Write the documents to the index
	    for (File documentFile : documentFiles) {
	    	
	    	try {
	    		Document doc = new Document();
		    	
//	    		Content of the File
	    		String fileExtract = FileUtils.readFileToString(documentFile, StandardCharsets.UTF_8);
	    		Field docExtractField =  new Field(ProjectConstants.DOC_FIELD_EXTRACT_STRING, fileExtract, TextField.TYPE_STORED);
	    		doc.add(docExtractField);
		    	
//		    	Name of the file 
		    	String fileNameString = documentFile.getName().toString().replace(".txt", "");
		    	Field docNameField = new Field(ProjectConstants.DOC_FIELD_NAME_STRING, fileNameString, TextField.TYPE_STORED);
		    	doc.add(docNameField);
		    	
		    	iwriter.addDocument(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
		}
	  	iwriter.close();
	}
	
    private static void clearDirectory(Path directory) throws IOException {
        // Walk through the directory and delete all files and sub-directories
        Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
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

