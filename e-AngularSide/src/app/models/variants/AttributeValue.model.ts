export interface AttributeValueRequest {
  value: string;
  attributeId: number;
}

export interface AttributeValueResponse {
  id: number;
  value: string;
  attributeId: number;
  attributeName: string;
}