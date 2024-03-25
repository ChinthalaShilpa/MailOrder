export class Drug{
	drugId:string = '';
	drugName:string ='';
	manufacturerName:string = '';
    manufacturedDate :Date = new Date();
	expiryDate :Date = new Date();
	quantity : number = 0;
    location : string = '';

    constructor(drugId:string, drugName:string, manufacturerName:string, manufacturedDate:Date, expiryDate:Date, quantity : number,location : string ){
            this.drugId = drugId;
            this.drugName = drugName;
            this.manufacturerName=manufacturerName;
            this.manufacturedDate = manufacturedDate;
            this.expiryDate = expiryDate;
            this.quantity= quantity;
            this.location = location;
    
       }

}