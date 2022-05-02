export interface BannerItem {
  link?: String; 
  image?: string;
  toNewWindow?: Boolean;
}

export interface Banner {
  _id?: number;
  __v?: number;

  bannerName: string;
  banners?: BannerItem[];

  titles: string[];
  author?: User;
}

export interface User {
  userName?: string;
  password?: string;
}