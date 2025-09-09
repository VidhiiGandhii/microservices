package com.micro.cast.models;

public class Casting {

    String _castingId;
    String _actorName;
    String _role;
    String _status;   // e.g. Confirmed, Pending, Rejected
    String _projectId; // foreign key to link with Project

    public Casting() {
    }

    public Casting(String castingId, String actorName, String role, String status, String projectId) {
        this._castingId = castingId;
        this._actorName = actorName;
        this._role = role;
        this._status = status;
        this._projectId = projectId;
    }

    public String getCastingId() {
        return _castingId;
    }

    public void setCastingId(String _castingId) {
        this._castingId = _castingId;
    }

    public String getActorName() {
        return _actorName;
    }

    public void setActorName(String _actorName) {
        this._actorName = _actorName;
    }

    public String getRole() {
        return _role;
    }

    public void setRole(String _role) {
        this._role = _role;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String _status) {
        this._status = _status;
    }

    public String getProjectId() {
        return _projectId;
    }

    public void setProjectId(String _projectId) {
        this._projectId = _projectId;
    }
}
