import { invoke } from "@tauri-apps/api/core";

interface Insets {
  top: number;
  bottom: number;
  left: number;
  right: number;
}

interface WindowInsets {
  systemBars: Insets;
  gestureInsets: Insets;
}

// export async function getInsets(): Promise<WindowInsets> {
//   return await invoke("plugin:window-insets|getInsets");
// }
// import { invoke } from '@tauri-apps/api/core'

export async function ping(): Promise<WindowInsets> {
  return await invoke("plugin:window-insets|ping");
}
