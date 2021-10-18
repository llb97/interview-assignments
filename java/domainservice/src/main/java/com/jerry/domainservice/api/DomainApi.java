/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.29).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.jerry.domainservice.api;

import com.jerry.domainservice.api.model.GenerateRequestDto;
import com.jerry.domainservice.api.model.GenerateResponseDto;
import com.jerry.domainservice.api.model.GetResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@Validated
public interface DomainApi {

    @Operation(summary = "Your GET endpoint", description = "Get domain name by the specific short domain information.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetResponseDto.class))) })
    @RequestMapping(value = "/domain/get/{shortDomainInformation}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<GetResponseDto> getDomainGet(@Parameter(in = ParameterIn.PATH, description = "Short Domain Information", required=true, schema=@Schema()) @PathVariable("shortDomainInformation") String shortDomainInformation);


    @Operation(summary = "", description = "Generate the short domain information of the input.", tags={ "Generate Domain" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenerateResponseDto.class))) })
    @RequestMapping(value = "/domain/generate",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<GenerateResponseDto> putDomainGenerate(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody GenerateRequestDto body);

}

