export class Student {
    public id?: number;
    public firstName: string;
    public lastName: string;
    public email?: string;
    public dob?: Date;
    public age?: number;

    constructor(firstName: string, lastName:string, email?: string, dob?: Date, age?: number, id?: number){
        this.id = id
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.age = age;
    } 
}