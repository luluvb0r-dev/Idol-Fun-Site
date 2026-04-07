import { requestApi, SITE_KEY } from './api-client';

export type SiteInfo = {
    siteKey: string;
    siteName: string;
    idolName: string;
    theme: {
        primaryColorHex: string;
        secondaryColorHex: string;
        accentColorHex: string;
    };
};

export async function fetchSite(): Promise<SiteInfo> {
    return requestApi<SiteInfo>(`sites/${SITE_KEY}`);
}
