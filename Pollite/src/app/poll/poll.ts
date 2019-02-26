// use http://www.jsontots.com for help

export interface Option {
  text: string;
  votes: number;
  uuid: string;
}

export interface Poll {
  prompt: string;
  options: Option[];
}
