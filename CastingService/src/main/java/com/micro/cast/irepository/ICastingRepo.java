package com.micro.cast.irepository;

import java.util.List;

import com.micro.cast.models.Casting;

public interface ICastingRepo {
    
    // Fetch all casting records
    List<Casting> GetAllCastings();

    // Fetch casting details by castingId
    Casting GetCastingById(String castingId);

    // Fetch casting details by actor name
    Casting GetCastingByActorName(String actorName);

    // Add a new casting record
    Casting AddCasting(Casting casting);

    // Count all casting records
    Integer GetCastingCount();

    // Get castingId by actor name
    String GetCastingIdByActorName(String actorName);

    // Get actor name by castingId
    String GetActorNameByCastingId(String castingId);

     String GetActorNameById(String castingId);

     List<Casting> GetCastingsByProjectId(String projectId);
}
