use serde::de::DeserializeOwned;
use tauri::{plugin::PluginApi, AppHandle, Runtime};

use crate::models::*;

pub fn init<R: Runtime, C: DeserializeOwned>(
    app: &AppHandle<R>,
    _api: PluginApi<R, C>,
) -> crate::Result<WindowInsets<R>> {
    Ok(WindowInsets(app.clone()))
}

/// Access to the window-insets APIs.
pub struct WindowInsets<R: Runtime>(AppHandle<R>);

impl<R: Runtime> WindowInsets<R> {
    pub fn get_insets(&self) -> crate::Result<Insets> {
        Ok(Insets {
            top: 0,
            bottom: 0,
            left: 0,
            right: 0,
        })
    }
}