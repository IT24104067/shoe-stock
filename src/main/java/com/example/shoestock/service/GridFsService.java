package com.example.shoestock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.bson.types.ObjectId;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.io.IOException;
import java.io.InputStream;

@Service
public class GridFsService {
    private final GridFsTemplate gridFsTemplate;

    public GridFsService(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    public String store(MultipartFile file, String filename) throws IOException {
        ObjectId id = gridFsTemplate.store(file.getInputStream(), filename, file.getContentType());
        return id.toHexString();
    }

    public GridFsResource getResource(String id) {
        GridFSFile file = gridFsTemplate.findOne(query(where("_id").is(new ObjectId(id))));
        return gridFsTemplate.getResource(file);
    }
}
