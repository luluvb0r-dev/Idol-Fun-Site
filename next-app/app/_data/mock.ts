export type Article = {
    id: string;
    title: string;
    lead: string;
    category: string;
    publishedAt: string;
    tags: string[];
    memberSlug: string;
    memberName: string;
    body: string[];
};

export type Member = {
    slug: string;
    name: string;
    role: string;
    catchCopy: string;
    themeColor: string;
    stats: {
        label: string;
        value: string;
    }[];
    profile: string;
};

export const members: Member[] = [
    {
        slug: 'hana',
        name: '花音',
        role: '=LOVE テイストの王道キュート担当',
        catchCopy: '=LOVE の多幸感をイメージした、透明感のあるセンター像。',
        themeColor: 'ローズクォーツ',
        stats: [
            { label: '推しポイント', value: 'ストロベリーソーダ感' },
            { label: 'ムード', value: 'スイートポップ' },
            { label: 'ライブMC', value: 'やわらかく明るい' },
        ],
        profile:
            '=LOVE ファンサイトの仮プロフィールとして、多幸感のある歌声と親しみやすい空気感を表現したメンバー像を置いています。',
    },
    {
        slug: 'mio',
        name: '美桜',
        role: '=LOVE テイストのパフォーマンス担当',
        catchCopy: '=LOVE らしい上品さとキレのあるステージ映えを意識した存在。',
        themeColor: 'ブラッシュコーラル',
        stats: [
            { label: '推しポイント', value: 'ピーチティー感' },
            { label: 'ムード', value: 'クールキュート' },
            { label: '特長', value: 'シャープなダンス' },
        ],
        profile:
            '=LOVE の楽曲世界を映す仮プロフィールとして、しなやかなパフォーマンスと表情変化の強さを持つメンバー像を置いています。',
    },
];

export const articles: Article[] = [
    {
        id: 'spring-tour-preview',
        title: '=LOVE Spring Tour Preview',
        lead: '=LOVE の新シーズンを想定し、セットリスト候補と会場ごとの見どころをまとめた導入記事です。',
        category: 'ライブ特集',
        publishedAt: '2026.03.28',
        tags: ['ツアー', 'セットリスト', '導入'],
        memberSlug: 'hana',
        memberName: '花音',
        body: [
            'オープニングは高揚感のあるナンバーから入り、照明演出は =LOVE を連想させる柔らかなピンクを基調に構成しています。',
            '中盤ではメンバーごとの個性が伝わるブロックを用意し、終盤に向けて一体感を高める設計です。ファン目線で追いやすい導線も意識しています。',
        ],
    },
    {
        id: 'member-style-note',
        title: '=LOVE Member Style Note',
        lead: '衣装、ヘア、ステージ上の立ち振る舞いから見えるメンバーの世界観を整理した読み物です。',
        category: '特集記事',
        publishedAt: '2026.03.26',
        tags: ['衣装', 'ステージ', 'メンバー'],
        memberSlug: 'mio',
        memberName: '美桜',
        body: [
            '淡いピンクを土台にしながら、シルエットはシャープに寄せることで、=LOVE らしい甘さと芯の強さの両立を表現しています。',
            '目線や手の角度まで丁寧にコントロールされていて、写真でも動画でも印象がぶれにくいのが魅力です。',
        ],
    },
    {
        id: 'fan-guide',
        title: '=LOVE First Fan Guide',
        lead: '初めて現場へ行く人でも迷いにくいように、観るポイントと楽しみ方の導線をまとめています。',
        category: 'ガイド',
        publishedAt: '2026.03.24',
        tags: ['初心者', 'ガイド', 'ファンUX'],
        memberSlug: 'hana',
        memberName: '花音',
        body: [
            '一覧から詳細へ、さらにメンバープロフィールへ自然に移動できる導線をイメージしながら、情報の入口をわかりやすく整理しています。',
            '今後 API 接続を入れる前提でも、現時点で体験の雰囲気が掴めるように仮データベースで骨格を先に作っています。',
        ],
    },
];

export function getArticleById(id: string) {
    return articles.find((article) => article.id === id);
}

export function getMemberBySlug(slug: string) {
    return members.find((member) => member.slug === slug);
}
