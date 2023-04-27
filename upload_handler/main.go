package http

import (
	"context"
	"fmt"
	"log"
	"os"
	"strings"

	"cloud.google.com/go/firestore"
	"google.golang.org/api/iterator"
)

type GCSEvent struct {
	Bucket    string `json:"bucket"`
	Name      string `json:"name"`
	MediaLink string `json:"mediaLink"`
}

// when a file is uploaded to the bucket, this Cloud function gets triggerd.
// The the identifier id of the file in the GCS bucket is set

func UploadHandler(ctx context.Context, e GCSEvent) error {
	
	return nil
}
