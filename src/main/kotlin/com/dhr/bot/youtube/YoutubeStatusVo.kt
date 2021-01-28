package com.dhr.bot.youtube

data class YoutubeStatusVo(
    val messages: List<Message>,
    val microformat: Microformat,
    val playabilityStatus: PlayabilityStatus,
    val responseContext: ResponseContext,
    val trackingParams: String,
    val videoDetails: VideoDetailsX
)

data class Message(
    val mealbarPromoRenderer: MealbarPromoRenderer,
    val tooltipRenderer: TooltipRenderer
)

data class Microformat(
    val microformatDataRenderer: MicroformatDataRenderer
)

data class PlayabilityStatus(
    val contextParams: String,
    val liveStreamability: LiveStreamability,
    val miniplayer: Miniplayer,
    val playableInEmbed: Boolean,
    val reason: String,
    val status: String
)

data class ResponseContext(
    val mainAppWebResponseContext: MainAppWebResponseContext,
    val serviceTrackingParams: List<ServiceTrackingParam>,
    val webResponseContextExtensionData: WebResponseContextExtensionData
)

data class VideoDetailsX(
    val allowRatings: Boolean,
    val author: String,
    val averageRating: Int,
    val channelId: String,
    val isCrawlable: Boolean,
    val isLiveContent: Boolean,
    val isLowLatencyLiveStream: Boolean,
    val isOwnerViewing: Boolean,
    val isPrivate: Boolean,
    val isUnpluggedCorpus: Boolean,
    val isUpcoming: Boolean,
    val latencyClass: String,
    val lengthSeconds: String,
    val shortDescription: String,
    val thumbnail: ThumbnailXXXXX,
    val title: String,
    val videoId: String,
    val viewCount: String
)

data class MealbarPromoRenderer(
    val actionButton: ActionButton,
    val dismissButton: DismissButton,
    val icon: Icon,
    val impressionEndpoints: List<ImpressionEndpoint>,
    val isVisible: Boolean,
    val messageTexts: List<MessageText>,
    val messageTitle: MessageTitle,
    val style: String,
    val trackingParams: String,
    val triggerCondition: String
)

data class TooltipRenderer(
    val acceptButton: AcceptButton,
    val detailsText: DetailsText,
    val dismissStrategy: DismissStrategy,
    val promoConfig: PromoConfig,
    val suggestedPosition: SuggestedPosition,
    val targetId: String,
    val text: TextXXX,
    val trackingParams: String
)

data class ActionButton(
    val buttonRenderer: ButtonRenderer
)

data class DismissButton(
    val buttonRenderer: ButtonRendererX
)

data class Icon(
    val thumbnails: List<Thumbnail>
)

data class ImpressionEndpoint(
    val clickTrackingParams: String,
    val commandMetadata: CommandMetadataXXX,
    val feedbackEndpoint: FeedbackEndpointXX
)

data class MessageText(
    val runs: List<RunXX>
)

data class MessageTitle(
    val runs: List<RunXXX>
)

data class ButtonRenderer(
    val navigationEndpoint: NavigationEndpoint,
    val serviceEndpoint: ServiceEndpoint,
    val size: String,
    val style: String,
    val text: Text,
    val trackingParams: String
)

data class NavigationEndpoint(
    val browseEndpoint: BrowseEndpoint,
    val clickTrackingParams: String,
    val commandMetadata: CommandMetadata
)

data class ServiceEndpoint(
    val clickTrackingParams: String,
    val commandMetadata: CommandMetadataX,
    val feedbackEndpoint: FeedbackEndpoint
)

data class Text(
    val runs: List<Run>
)

data class BrowseEndpoint(
    val browseId: String,
    val params: String
)

data class CommandMetadata(
    val webCommandMetadata: WebCommandMetadata
)

data class WebCommandMetadata(
    val apiUrl: String,
    val rootVe: Int,
    val url: String,
    val webPageType: String
)

data class CommandMetadataX(
    val webCommandMetadata: WebCommandMetadataX
)

data class FeedbackEndpoint(
    val feedbackToken: String,
    val uiActions: UiActions
)

data class WebCommandMetadataX(
    val apiUrl: String,
    val sendPost: Boolean
)

data class UiActions(
    val hideEnclosingContainer: Boolean
)

data class Run(
    val text: String
)

data class ButtonRendererX(
    val serviceEndpoint: ServiceEndpointX,
    val size: String,
    val style: String,
    val text: TextX,
    val trackingParams: String
)

data class ServiceEndpointX(
    val clickTrackingParams: String,
    val commandMetadata: CommandMetadataXX,
    val feedbackEndpoint: FeedbackEndpointX
)

data class TextX(
    val runs: List<RunX>
)

data class CommandMetadataXX(
    val webCommandMetadata: WebCommandMetadataXX
)

data class FeedbackEndpointX(
    val feedbackToken: String,
    val uiActions: UiActionsX
)

data class WebCommandMetadataXX(
    val apiUrl: String,
    val sendPost: Boolean
)

data class UiActionsX(
    val hideEnclosingContainer: Boolean
)

data class RunX(
    val text: String
)

data class Thumbnail(
    val height: Int,
    val url: String,
    val width: Int
)

data class CommandMetadataXXX(
    val webCommandMetadata: WebCommandMetadataXXX
)

data class FeedbackEndpointXX(
    val feedbackToken: String,
    val uiActions: UiActionsXX
)

data class WebCommandMetadataXXX(
    val apiUrl: String,
    val sendPost: Boolean
)

data class UiActionsXX(
    val hideEnclosingContainer: Boolean
)

data class RunXX(
    val text: String
)

data class RunXXX(
    val text: String
)

data class AcceptButton(
    val buttonRenderer: ButtonRendererXX
)

data class DetailsText(
    val runs: List<RunXXXXX>
)

data class DismissStrategy(
    val type: String
)

data class PromoConfig(
    val acceptCommand: AcceptCommand,
    val dismissCommand: DismissCommand,
    val impressionEndpoints: List<ImpressionEndpointX>,
    val promoId: String
)

data class SuggestedPosition(
    val type: String
)

data class TextXXX(
    val runs: List<RunXXXXXX>
)

data class ButtonRendererXX(
    val command: Command,
    val size: String,
    val style: String,
    val text: TextXX,
    val trackingParams: String
)

data class Command(
    val clickTrackingParams: String,
    val commandMetadata: CommandMetadataXXXX,
    val feedbackEndpoint: FeedbackEndpointXXX
)

data class TextXX(
    val runs: List<RunXXXX>
)

data class CommandMetadataXXXX(
    val webCommandMetadata: WebCommandMetadataXXXX
)

data class FeedbackEndpointXXX(
    val feedbackToken: String,
    val uiActions: UiActionsXXX
)

data class WebCommandMetadataXXXX(
    val apiUrl: String,
    val sendPost: Boolean
)

data class UiActionsXXX(
    val hideEnclosingContainer: Boolean
)

data class RunXXXX(
    val text: String
)

data class RunXXXXX(
    val text: String
)

data class AcceptCommand(
    val clickTrackingParams: String,
    val commandMetadata: CommandMetadataXXXXX,
    val feedbackEndpoint: FeedbackEndpointXXXX
)

data class DismissCommand(
    val clickTrackingParams: String,
    val commandMetadata: CommandMetadataXXXXXX,
    val feedbackEndpoint: FeedbackEndpointXXXXX
)

data class ImpressionEndpointX(
    val clickTrackingParams: String,
    val commandMetadata: CommandMetadataXXXXXXX,
    val feedbackEndpoint: FeedbackEndpointXXXXXX
)

data class CommandMetadataXXXXX(
    val webCommandMetadata: WebCommandMetadataXXXXX
)

data class FeedbackEndpointXXXX(
    val feedbackToken: String,
    val uiActions: UiActionsXXXX
)

data class WebCommandMetadataXXXXX(
    val apiUrl: String,
    val sendPost: Boolean
)

data class UiActionsXXXX(
    val hideEnclosingContainer: Boolean
)

data class CommandMetadataXXXXXX(
    val webCommandMetadata: WebCommandMetadataXXXXXX
)

data class FeedbackEndpointXXXXX(
    val feedbackToken: String,
    val uiActions: UiActionsXXXXX
)

data class WebCommandMetadataXXXXXX(
    val apiUrl: String,
    val sendPost: Boolean
)

data class UiActionsXXXXX(
    val hideEnclosingContainer: Boolean
)

data class CommandMetadataXXXXXXX(
    val webCommandMetadata: WebCommandMetadataXXXXXXX
)

data class FeedbackEndpointXXXXXX(
    val feedbackToken: String,
    val uiActions: UiActionsXXXXXX
)

data class WebCommandMetadataXXXXXXX(
    val apiUrl: String,
    val sendPost: Boolean
)

data class UiActionsXXXXXX(
    val hideEnclosingContainer: Boolean
)

data class RunXXXXXX(
    val text: String
)

data class MicroformatDataRenderer(
    val androidPackage: String,
    val appName: String,
    val availableCountries: List<String>,
    val category: String,
    val description: String,
    val embedDetails: EmbedDetails,
    val familySafe: Boolean,
    val iosAppArguments: String,
    val iosAppStoreId: String,
    val linkAlternates: List<LinkAlternate>,
    val noindex: Boolean,
    val ogType: String,
    val pageOwnerDetails: PageOwnerDetails,
    val paid: Boolean,
    val publishDate: String,
    val schemaDotOrgType: String,
    val siteName: String,
    val thumbnail: ThumbnailX,
    val title: String,
    val twitterCardType: String,
    val twitterSiteHandle: String,
    val unlisted: Boolean,
    val uploadDate: String,
    val urlApplinksAndroid: String,
    val urlApplinksIos: String,
    val urlApplinksWeb: String,
    val urlCanonical: String,
    val urlTwitterAndroid: String,
    val urlTwitterIos: String,
    val videoDetails: VideoDetails,
    val viewCount: String
)

data class EmbedDetails(
    val flashSecureUrl: String,
    val flashUrl: String,
    val height: Int,
    val iframeUrl: String,
    val width: Int
)

data class LinkAlternate(
    val alternateType: String,
    val hrefUrl: String,
    val title: String
)

data class PageOwnerDetails(
    val externalChannelId: String,
    val name: String,
    val youtubeProfileUrl: String
)

data class ThumbnailX(
    val thumbnails: List<ThumbnailXX>
)

data class VideoDetails(
    val externalVideoId: String
)

data class ThumbnailXX(
    val height: Int,
    val url: String,
    val width: Int
)

data class LiveStreamability(
    val liveStreamabilityRenderer: LiveStreamabilityRenderer
)

data class Miniplayer(
    val miniplayerRenderer: MiniplayerRenderer
)

data class LiveStreamabilityRenderer(
    val offlineSlate: OfflineSlate,
    val pollDelayMs: String,
    val videoId: String
)

data class OfflineSlate(
    val liveStreamOfflineSlateRenderer: LiveStreamOfflineSlateRenderer
)

data class LiveStreamOfflineSlateRenderer(
    val mainText: MainText,
    val offlineSlateStyle: String,
    val scheduledStartTime: String,
    val subtitleText: SubtitleText,
    val thumbnail: ThumbnailXXX
)

data class MainText(
    val runs: List<RunXXXXXXX>
)

data class SubtitleText(
    val simpleText: String
)

data class ThumbnailXXX(
    val thumbnails: List<ThumbnailXXXX>
)

data class RunXXXXXXX(
    val text: String
)

data class ThumbnailXXXX(
    val height: Int,
    val url: String,
    val width: Int
)

data class MiniplayerRenderer(
    val playbackMode: String
)

data class MainAppWebResponseContext(
    val loggedOut: Boolean
)

data class ServiceTrackingParam(
    val params: List<Param>,
    val service: String
)

data class WebResponseContextExtensionData(
    val hasDecorated: Boolean
)

data class Param(
    val key: String,
    val value: String
)

data class ThumbnailXXXXX(
    val thumbnails: List<ThumbnailXXXXXX>
)

data class ThumbnailXXXXXX(
    val height: Int,
    val url: String,
    val width: Int
)