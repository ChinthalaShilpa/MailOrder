export class JwtResponse{
token: string = '';
constructor(token: string){
    this.token = token
}

getToken(): string{
    return this.token;
}
}