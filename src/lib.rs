use tauri::{
    plugin::{Builder, TauriPlugin},
    Manager, Runtime,
};

pub use models::*;

#[cfg(desktop)]
mod desktop;
#[cfg(mobile)]
mod mobile;

mod commands;
mod error;
mod models;

pub use error::{Error, Result};

#[cfg(desktop)]
use desktop::WindowInsets;
#[cfg(mobile)]
use mobile::WindowInsets;

/// Extensions to [`tauri::App`], [`tauri::AppHandle`] and [`tauri::Window`] to access the window-insets APIs.
pub trait WindowInsetsExt<R: Runtime> {
    fn window_insets(&self) -> &WindowInsets<R>;
}

impl<R: Runtime, T: Manager<R>> crate::WindowInsetsExt<R> for T {
    fn window_insets(&self) -> &WindowInsets<R> {
        self.state::<WindowInsets<R>>().inner()
    }
}

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
    Builder::new("window-insets")
        .invoke_handler(tauri::generate_handler![commands::get_insets])
        .setup(|app, api| {
            #[cfg(mobile)]
            let window_insets = mobile::init(app, api)?;
            #[cfg(desktop)]
            let window_insets = desktop::init(app, api)?;
            app.manage(window_insets);
            Ok(())
        })
        .build()
}
