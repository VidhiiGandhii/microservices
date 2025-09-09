package com.micro.cast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectDto {
@JsonProperty("title")
 String _projecttitle;
 @JsonProperty("type")
 String _type;
 @JsonProperty("status")
 String _status;
@JsonProperty("episodes")
 int _episodes;

public ProjectDto(){

}
public ProjectDto( String projecttitle, String type, String status, int episodes){
this._projecttitle=projecttitle;
this._episodes=episodes;
this._status=status;
this._type=type; 
}

    public String getProjecttitle() {
        return _projecttitle;
    }

    public String getType() {
        return _type;
    }

    public String getStatus() {
        return _status;
    }

    public int getEpisodes() {
        return _episodes;
    }

    public void setProjecttitle(String _projecttitle) {
        this._projecttitle = _projecttitle;
    }

    public void setType(String _type) {
        this._type = _type;
    }

    public void setStatus(String _status) {
        this._status = _status;
    }

    public void setEpisodes(int _episodes) {
        this._episodes = _episodes;
    }
    


}
