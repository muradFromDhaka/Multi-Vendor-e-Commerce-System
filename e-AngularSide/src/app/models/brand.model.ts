export interface BrandRequest {
  name: string;
  description?: string;
  logoUrl?: string; // optional, frontend থেকে file upload এর জন্য FormData use হবে
}

export interface BrandResponse {
  id: number;
  name: string;
  description?: string;
  logoUrl?: string;
}
