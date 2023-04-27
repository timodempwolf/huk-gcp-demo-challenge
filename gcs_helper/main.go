package http

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"mime"
	"net/http"
	"time"

	"cloud.google.com/go/storage"
	"github.com/GoogleCloudPlatform/functions-framework-go/functions"
)

func init() {
	functions.HTTP("GcsHelper", GcsHelper)
}

// generate a sigend url for the passed GCS bucket, to allow the frontend to upload a file to the bucket without authentication

func GcsHelper(w http.ResponseWriter, r *http.Request) {

}
