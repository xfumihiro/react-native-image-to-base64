#import "RNImageToBase64.h"
#import <AssetsLibrary/AssetsLibrary.h>
#import <UIKit/UIKit.h>

@implementation RNImageToBase64

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(getBase64String:(NSString *)input callback:(RCTResponseSenderBlock)callback)
{
  NSURL *url = [[NSURL alloc] initWithString:input];
  ALAssetsLibrary *library = [[ALAssetsLibrary alloc] init];
  [library assetForURL:url resultBlock:^(ALAsset *asset) {
    ALAssetRepresentation *rep = [asset defaultRepresentation];
    CGImageRef imageRef = [rep fullScreenImage];
    NSData *imageData = UIImagePNGRepresentation([UIImage imageWithCGImage:imageRef]);
    NSString *base64Encoded = [imageData base64EncodedStringWithOptions:0];
    callback(@[[NSNull null], base64Encoded]);
  } failureBlock:^(NSError *error) {
    NSLog(@"that didn't work %@", error);
    callback(@[error]);
  }];
}

@end
