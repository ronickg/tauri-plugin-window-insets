use tauri::{command, AppHandle, Runtime};

use crate::models::*;
use crate::Result;
use crate::WindowInsetsExt;

#[command]
pub(crate) async fn get_insets<R: Runtime>(app: AppHandle<R>) -> Result<Insets> {
    app.window_insets().get_insets()
}
