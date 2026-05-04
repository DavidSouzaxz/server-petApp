package com.project.pettvaccine_api.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CloudinaryService {

    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    public Map<String, Object> generateSignature() {
        // A biblioteca espera "cloud_name", "api_key" e "api_secret"
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));

        long timestamp = System.currentTimeMillis() / 1000L;
        Map<String, Object> params = new TreeMap<>();
        params.put("timestamp", timestamp);

        // Gera a assinatura usando o secret real
        String signature = cloudinary.apiSignRequest(params, apiSecret);

        // Retorna os dados para o Front-end
        return ObjectUtils.asMap(
                "signature", signature,
                "timestamp", timestamp,
                "api_key", apiKey,
                "cloud_name", cloudName
        );
    }

    public void deleteImage(String photoUrl) {
        try {
            String publicId = photoUrl.substring(photoUrl.lastIndexOf("/") + 1, photoUrl.lastIndexOf("."));

            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloudName,
                    "api_key", apiKey,
                    "api_secret", apiSecret,
                    "secure", true
            ));

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println("Imagem removida do Cloudinary: " + publicId);
        } catch (Exception e) {
            System.err.println("Erro ao deletar imagem do Cloudinary: " + e.getMessage());
        }
    }
}