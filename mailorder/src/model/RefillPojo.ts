export class RefillPojo{
    statusOk:boolean = false;
    mid:string='';
	sid:number = 0;

    constructor(statusOk:boolean,mid:string, sid:number)
    {
        this.statusOk = statusOk;
        this.mid = mid;
        this.sid = sid;
    }
 
}