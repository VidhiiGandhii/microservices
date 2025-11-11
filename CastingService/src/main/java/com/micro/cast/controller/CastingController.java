package com.micro.cast.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException; // For DB errors
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.cast.dto.CastingDto;
import com.micro.cast.dto.CastingResponseDto;
import com.micro.cast.exception.CastingNotFoundException;
import com.micro.cast.exception.InvalidCastingException;
import com.micro.cast.exception.ProjectServiceOfflineException;
import com.micro.cast.models.Casting;
import com.micro.cast.services.CastingService;

@RestController
@RequestMapping("/casting")
public class CastingController {

    private final CastingService castingService;
    private final ModelMapper modelMapper; // We need this to map back

    @Autowired
    public CastingController(CastingService castingService, ModelMapper modelMapper) {
        this.castingService = castingService;
        this.modelMapper = modelMapper;
    }

    /**
     * Get all castings
     * Handles success, service offline errors, and general errors.
     */
    @GetMapping("/all")
    public ResponseEntity<CastingResponseDto> getAllCastings() {
        CastingResponseDto response = new CastingResponseDto();
        try {
            List<CastingDto> castings = castingService.getAllCastings();
            
            response.set_Status("Success");
            response.set_Castings(castings);
            return ResponseEntity.ok(response);

        } catch (ProjectServiceOfflineException ex) {
            // --- Handle 'DBNotRunning' (but for a microservice) ---
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } catch (Exception ex) {
            response.set_Status("An internal server error occurred: " + ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Get casting by Id
     * Handles success, not found, service offline, and general errors.
     */
    @GetMapping("/{castingId}")
    public ResponseEntity<CastingResponseDto> getCastingById(@PathVariable String castingId) {
        CastingResponseDto response = new CastingResponseDto();
        try {
            CastingDto casting = castingService.getCastingById(castingId);
            
            response.set_Status("Success");
            response.set_Casting(casting);
            return ResponseEntity.ok(response);

        } catch (CastingNotFoundException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ProjectServiceOfflineException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } catch (Exception ex) {
            response.set_Status("An internal server error occurred: " + ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Get casting by Actor Name
     * Handles same errors as Get by ID.
     */
    @GetMapping("/actor/{actorName}")
    public ResponseEntity<CastingResponseDto> getCastingByActorName(@PathVariable String actorName) {
        CastingResponseDto response = new CastingResponseDto();
        try {
            CastingDto casting = castingService.getCastingByActorName(actorName);
            
            response.set_Status("Success");
            response.set_Casting(casting);
            return ResponseEntity.ok(response);

        } catch (CastingNotFoundException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ProjectServiceOfflineException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } catch (Exception ex) {
            response.set_Status("An internal server error occurred: " + ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Add new casting
     * Handles success, bad request, DB errors, and general errors.
     */
    @PostMapping
    public ResponseEntity<CastingResponseDto> addCasting(@RequestBody CastingDto castingDto) {
        CastingResponseDto response = new CastingResponseDto();
        try {
            // Service method now returns the saved Casting object
            Casting savedCasting = castingService.addCasting(castingDto);
            
            response.set_Status("Success: Casting added with ID: " + savedCasting.getCastingId());
            response.set_Casting(modelMapper.map(savedCasting, CastingDto.class));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (InvalidCastingException ex) {
            // --- Handle Bad Data (like 'Credit cannot be 0') ---
            response.set_Status(ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (DataAccessException ex) {
            // --- Handle 'DBNotRunning' or Duplicate Key, etc. ---
            response.set_Status("Database error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } catch (Exception ex) {
            response.set_Status("An internal server error occurred: " + ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Get total casting count
     * Simple endpoint, but we can still wrap it.
     */
    @GetMapping("/count")
    public ResponseEntity<?> getCastingCount() {
        // This is simple, we can just return the number directly
        // or wrap it in a DTO if we want.
        try {
            Integer count = castingService.getCastingCount();
            // Returning in a simple map for clarity
            return ResponseEntity.ok().body(java.util.Collections.singletonMap("count", count));
        } catch (Exception ex) {
            CastingResponseDto response = new CastingResponseDto();
            response.set_Status("Could not retrieve count: " + ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Add casting AND project
     * Handles the most complex set of errors.
     */
    @PostMapping("/addWithProject")
    public ResponseEntity<CastingResponseDto> addCastingWithProject(@RequestBody CastingDto requestDto) {
        CastingResponseDto response = new CastingResponseDto();
        try {
            Casting savedCasting = castingService.addCastingWithProject(requestDto);
            
            response.set_Status("Success: Casting added with ID: " + savedCasting.getCastingId());
            response.set_Casting(modelMapper.map(savedCasting, CastingDto.class));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (InvalidCastingException ex) {
            // --- Handle Bad Data ---
            response.set_Status(ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (ProjectServiceOfflineException ex) {
            // --- Handle Downstream Service Error ---
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } catch (DataAccessException ex) {
            // --- Handle DB Error ---
            response.set_Status("Database error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } catch (Exception ex) {
            response.set_Status("An internal server error occurred: " + ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}