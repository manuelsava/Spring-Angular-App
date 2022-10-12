export class University {
    public id: number;
    public name: string;
    public signature: string;
    public address: string;

    constructor(id: number, name: string, signature:string, address: string){
        this.id = id;
        this.name = name;
        this.signature = signature;
        this.address = address;
    } 
}