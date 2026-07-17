export interface AttributeRequest {
  name: string;
  categoryId: number;
}

export interface AttributeResponse {
  id: number;
  name: string;

  categoryId: number;
  categoryName: string;
}


// -------------------frontend theke create-------------------------

export interface DynamicAttribute {

  attributeId: number;

  attributeName: string;

  values: any[];

  selectedValueId?: number;

}