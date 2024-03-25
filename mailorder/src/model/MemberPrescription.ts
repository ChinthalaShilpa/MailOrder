export class MemberPrescription{
    memberId:string = '';
	memberLocation:string = '';
	date :string = '';
	quantity:number = 0;
	drugName:string = '';
	doctorName:string = '';
	course:string = '';
    constructor(
	memberId:string,
	memberLocation:string ,
	date :string ,
	quantity:number ,
	drugName:string ,
	doctorName:string ,
	course:string 
    ){
       this.memberId = memberId;
       this.memberLocation = memberLocation;
       this.date = date;
       this.quantity = quantity;
       this.drugName =drugName;
       this.doctorName = doctorName;
       this.course = course
    }

    
}