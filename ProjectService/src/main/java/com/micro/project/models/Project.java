package com.micro.project.models;
public class Project {

 String _projecttitle;
 String _type;
 String _status;
 String _projectId;
 int _episodes;

public Project(){

}
public Project( String projectId,String projecttitle, String type, String status, int episodes){
    this._projectId=projectId;
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
    public String get_projectId() {
        return _projectId;
    }
    public void set_projectId(String _projectId) {
        this._projectId = _projectId;
    }
    


}
