export interface MenuItem {
  title: string;
  type?: string;
  active?: boolean;
  submenus?: MenuItem[];
}
