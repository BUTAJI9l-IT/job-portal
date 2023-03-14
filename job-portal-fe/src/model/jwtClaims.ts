export interface JwtClaims {
  sub?: string;
  nui?: string;
  scope: JwtClaims.ScopeEnum;
  exp: bigint,
  iat: bigint,
  jti?: string,
  email?: string
  lang?: string
}

export namespace JwtClaims {
  export type ScopeEnum = 'ADMIN' | 'COMPANY' | 'REGULAR_USER';
  export const ScopeEnum = {
    ADMIN: 'ADMIN' as ScopeEnum,
    COMPANY: 'COMPANY' as ScopeEnum,
    REGULARUSER: 'REGULAR_USER' as ScopeEnum
  };
}
