use serde::de::DeserializeOwned;
use tauri::{
    plugin::{PluginApi, PluginHandle},
    AppHandle, Runtime,
};

use crate::models::*;

#[cfg(target_os = "ios")]
tauri::ios_plugin_binding!(init_plugin_window_insets);

// initializes the Kotlin or Swift plugin classes
pub fn init<R: Runtime, C: DeserializeOwned>(
    _app: &AppHandle<R>,
    api: PluginApi<R, C>,
) -> crate::Result<WindowInsets<R>> {
    #[cfg(target_os = "android")]
    let handle = api.register_android_plugin("com.plugin.insets", "ExamplePlugin")?;
    #[cfg(target_os = "ios")]
    let handle = api.register_ios_plugin(init_plugin_window_insets)?;
    Ok(WindowInsets(handle))
}

/// Access to the window-insets APIs.
pub struct WindowInsets<R: Runtime>(PluginHandle<R>);

impl<R: Runtime> WindowInsets<R> {
    pub fn ping(&self) -> crate::Result<InsetResponse> {
        self.0.run_mobile_plugin("ping", ()).map_err(Into::into)
    }
}
