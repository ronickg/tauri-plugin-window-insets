use tauri::{command, AppHandle, Runtime};

use crate::models::*;
use crate::Result;
use crate::WindowInsetsExt;

// #[command]
// pub(crate) async fn ping<R: Runtime>(
//     app: AppHandle<R>,
//     payload: PingRequest,
// ) -> Result<PingResponse> {
//     app.window_insets().ping(payload)
// }

#[command]
pub(crate) async fn ping<R: Runtime>(app: AppHandle<R>) -> Result<InsetResponse> {
    app.window_insets().ping()
}
