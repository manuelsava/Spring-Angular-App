export class Course {
    public id?: number;
    public name: string;
    public description: string;
    public cfu: number;
    public professor: string;
    public year: number;
    public active: boolean;

    constructor(name: string, description: string, cfu: number, professor: string, year: number, active: boolean, id?: number) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cfu = cfu;
        this.professor = professor;
        this.year = year;
        this.active = active;
    }
}