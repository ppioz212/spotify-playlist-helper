package com.asuresh.spotifyplaylistcompiler.model;
import com.asuresh.spotifyplaylistcompiler.model.playlistmodel.*;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Playlist {
    private boolean collaborative;
    private String description;
    @SerializedName("external_urls")
    private ExternalUrls externalUrls;
    private String href;
    private String id;
    private ArrayList<Image> images;
    private String name;
    private Owner owner;
    @SerializedName("public")
    private boolean isPublic;
    @SerializedName("snapshot_id")
    private String snapshotId;
    private Tracks tracks;
    private String type;
    private String uri;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public String getDescription() {
        return description;
    }

    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}

