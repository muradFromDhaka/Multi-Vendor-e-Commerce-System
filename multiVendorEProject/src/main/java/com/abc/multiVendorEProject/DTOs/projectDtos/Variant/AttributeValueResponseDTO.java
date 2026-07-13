package com.abc.multiVendorEProject.DTOs.projectDtos.Variant;


public record AttributeValueResponseDTO (
     Long id,
     String value,

     Long attributeId,
     String attributeName
 ){}
