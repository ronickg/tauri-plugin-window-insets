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
    pub fn ping(&self) -> crate::Result<InsetResponse> {
        Ok(InsetResponse {
            system_bars: Insets {
                top: 0,
                bottom: 0,
                left: 0,
                right: 0,
            },
            gesture_insets: Insets {
                top: 0,
                bottom: 0,
                left: 0,
                right: 0,
            },
        })
    }
}

// impl<R: Runtime> WindowInsets<R> {
//     pub fn ping(&self) -> crate::Result<InsetResponse> {
//         // For desktop, return zero insets or implement platform-specific logic
//         Ok(InsetResponse {
//             system_bars: Insets {
//                 top: 0,
//                 bottom: 0,
//                 left: 0,
//                 right: 0,
//             },
//             gesture_insets: Insets {
//                 top: 0,
//                 bottom: 0,
//                 left: 0,
//                 right: 0,
//             },
//         })
//     }
// }
