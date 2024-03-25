import { DrugLocation } from "./DrugLocation";

export class DrugDetail{
	drugId:string = '';
	drugName:string ='';
	manufacturerName:string = '';
    manufacturedDate :Date = new Date();
	expiryDate :Date = new Date();
    drugLocationList:DrugLocation[] = [];
    
    // constructor(drugId:string, drugName:string, manufacturerName:string, manufacturedDate:Date, expiryDate:Date, drugLocationList:DrugLocation[]){
    //    this.drugId = drugId;
    //    this.drugName = drugName;
    //    this.manufacturerName=manufacturerName;
    //    this.manufacturedDate = manufacturedDate;
    //    this.expiryDate = expiryDate;
    //    this.drugLocationList = drugLocationList;

    // }
}