package uk.gov.dhsc.htbhf.eligibility.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
public class EligibilityResponse {

    @JsonProperty("eligibilityStatus")
    private EligibilityStatus eligibilityStatus;

    @JsonProperty("dwpHouseholdIdentifier")
    private String dwpHouseholdIdentifier;

    @JsonProperty("hmrcHouseholdIdentifier")
    private String hmrcHouseholdIdentifier;
}