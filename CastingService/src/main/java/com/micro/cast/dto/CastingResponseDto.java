package com.micro.cast.dto;

import java.util.List;

public class CastingResponseDto {

    private String _Status;

    // We include fields for both a single result and a list result
    private CastingDto _Casting;
    private List<CastingDto> _Castings;

    // Getters and Setters
    public String get_Status() {
        return _Status;
    }

    public void set_Status(String _Status) {
        this._Status = _Status;
    }

    public CastingDto get_Casting() {
        return _Casting;
    }

    public void set_Casting(CastingDto _Casting) {
        this._Casting = _Casting;
    }

    public List<CastingDto> get_Castings() {
        return _Castings;
    }

    public void set_Castings(List<CastingDto> _Castings) {
        this._Castings = _Castings;
    }
}