// use http://www.jsontots.com for help

interface Option {
  text: string;
  votes: number;
}

export interface Poll {
  prompt: string;
  options: Option[];
}
