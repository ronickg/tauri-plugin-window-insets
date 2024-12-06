import { invoke } from "@tauri-apps/api/core";

interface Insets {
  top: number;
  bottom: number;
  left: number;
  right: number;
}

export async function getInsets(): Promise<Insets> {
  return await invoke("plugin:window-insets|get_insets");
}
