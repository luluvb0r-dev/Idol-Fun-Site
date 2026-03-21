'use client';
import { useEffect, useState } from 'react';

export default function Home() {
    const [apiMessage, setApiMessage] = useState<string>('Loading...');

    useEffect(() => {
        fetch('http://localhost:8080/api/hello')
            .then((res) => res.json())
            .then((data) => setApiMessage(data.message))
            .catch((error) => {
                console.error('API Error:', error);
                setApiMessage('エラー: APIサーバーと通信できません');
            });
    }, []);

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-24 bg-gray-50">
            <h1 className="text-5xl font-extrabold text-blue-600 drop-shadow-sm">
                hello,world
            </h1>
            <p className="mt-4 text-xl text-gray-500 font-medium">
                Next.jsとSpring Bootを組み合わせたアプリケーションのフロントエンドです。
            </p>

            <div className="mt-10 p-6 bg-white rounded-xl shadow-md border border-gray-100 flex flex-col items-center hover:shadow-lg transition-shadow duration-300">
                <h2 className="text-sm font-semibold text-gray-400 tracking-wider uppercase mb-2">Message from Spring Boot</h2>
                <p className="text-2xl font-bold text-gray-800 bg-clip-text text-transparent bg-gradient-to-r from-green-400 to-blue-500">
                    {apiMessage}
                </p>
            </div>
        </main>
    );
}
