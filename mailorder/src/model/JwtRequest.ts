export class JwtRequest{
    userName: string = ''
    password: string = ''
    constructor(uName: string, password: string){
        this.userName = uName
        this.password = password
    }

    
}