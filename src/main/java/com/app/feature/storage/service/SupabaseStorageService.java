package com.app.feature.storage.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class SupabaseStorageService {

    private S3Client s3Client;

    @Value("${supabase.endpoint}")
    private String supabaseEndpoint;

    @Value("${supabase.bucket}")
    private String bucketName;

    @Autowired
    public SupabaseStorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file, String path) {
        try {
            // to identify the object uniquely.
            String key = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

            // Upload the file to S3 bucket
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(path + "/" + key)
                    .build(), RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return generatePublicUrl(path + "/" + key);

        } catch (IOException exception) {
            return exception.getMessage();
        }
    }

    private String generatePublicUrl(String filePath) {
        return supabaseEndpoint + "/storage/v1/object/public/" + bucketName + "/" + filePath;
    }
}